import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { MascotaService } from '../../services/mascota.service';
import { Mascota, CalendarioVacuna } from 'src/app/interfaces/mascota.interface';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-mascota-detail',
  templateUrl: './mascota-detail.component.html',
  styles: []
})
export class MascotaDetailComponent implements OnInit {

  mascota!: Mascota;
  calendarioVacunas: CalendarioVacuna[] = [];

  constructor(
    private mascotaService: MascotaService,
    private route: ActivatedRoute,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      const id = +params['id'];
      this.loadMascota(id);
      this.loadCalendario(id);
    });
  }

  loadMascota(id: number): void {
    this.mascotaService.get(id).subscribe({
      next: (mascota) => this.mascota = mascota,
      error: () => {
        Swal.fire('Error', 'Mascota no encontrada', 'error');
        this.router.navigate(['/mascotas']);
      }
    });
  }

  loadCalendario(mascotaId: number): void {
    this.mascotaService.getCalendarioByMascota(mascotaId).subscribe({
      next: (calendario) => this.calendarioVacunas = calendario,
      error: () => this.calendarioVacunas = []
    });
  }

  getEdad(): string {
    if (!this.mascota?.fechaNacimiento) return 'Desconocida';
    const nacimiento = new Date(this.mascota.fechaNacimiento);
    const hoy = new Date();
    let anios = hoy.getFullYear() - nacimiento.getFullYear();
    let meses = hoy.getMonth() - nacimiento.getMonth();
    if (meses < 0) {
      anios--;
      meses += 12;
    }
    if (anios > 0) return `${anios} año${anios > 1 ? 's' : ''} y ${meses} mes${meses !== 1 ? 'es' : ''}`;
    return `${meses} mes${meses !== 1 ? 'es' : ''}`;
  }

  getPendientes(): number {
    return this.calendarioVacunas.filter(c => !c.aplicada).length;
  }

  deleteMascota(): void {
    Swal.fire({
      title: '¿Eliminar mascota?',
      text: 'Esta acción no se puede deshacer.',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#2E8B57',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Sí, eliminar',
      cancelButtonText: 'Cancelar'
    }).then(result => {
      if (result.isConfirmed) {
        this.mascotaService.delete(this.mascota).subscribe({
          next: () => {
            Swal.fire({ title: 'Eliminada', icon: 'success', timer: 1500, showConfirmButton: false });
            this.router.navigate(['/mascotas']);
          },
          error: () => Swal.fire('Error', 'No se pudo eliminar', 'error')
        });
      }
    });
  }
}
