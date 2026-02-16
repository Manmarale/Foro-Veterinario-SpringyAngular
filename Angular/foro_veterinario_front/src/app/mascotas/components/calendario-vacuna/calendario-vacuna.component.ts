import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MascotaService } from '../../services/mascota.service';
import { Mascota, CalendarioVacuna, Vacuna } from 'src/app/interfaces/mascota.interface';
import { AuthService } from 'src/app/auth/services/auth.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-calendario-vacuna',
  templateUrl: './calendario-vacuna.component.html',
  styles: []
})
export class CalendarioVacunaComponent implements OnInit {

  mascota!: Mascota;
  calendarioVacunas: CalendarioVacuna[] = [];
  vacunasDisponibles: Vacuna[] = [];
  showForm = false;
  form!: FormGroup;

  constructor(
    private mascotaService: MascotaService,
    private authService: AuthService,
    private route: ActivatedRoute,
    private fb: FormBuilder
  ) { }

  ngOnInit(): void {
    this.form = this.fb.group({
      vacunaId:        ['', [Validators.required]],
      fechaAplicacion: [''],
      fechaProxima:    ['', [Validators.required]],
      notas:           ['', [Validators.maxLength(500)]],
      aplicada:        [false],
    });

    this.route.params.subscribe(params => {
      const id = +params['id'];
      this.loadMascota(id);
      this.loadCalendario(id);
    });
  }

  loadMascota(id: number): void {
    this.mascotaService.get(id).subscribe(mascota => {
      this.mascota = mascota;
      this.loadVacunas(mascota.especie);
    });
  }

  loadVacunas(especie: string): void {
    this.mascotaService.getVacunas().subscribe(vacunas => {
      this.vacunasDisponibles = vacunas;
    });
  }

  loadCalendario(mascotaId: number): void {
    this.mascotaService.getCalendarioByMascota(mascotaId).subscribe({
      next: (calendario) => this.calendarioVacunas = calendario,
      error: () => this.calendarioVacunas = []
    });
  }

  toggleForm(): void {
    this.showForm = !this.showForm;
    if (this.showForm) {
      this.form.reset({ aplicada: false });
    }
  }

  agregarVacuna(): void {
    if (this.form.invalid) return;

    const usuario = this.authService.usuario;
    const calendario: any = {
      mascotaId: this.mascota.id,
      vacunaId: +this.form.value.vacunaId,
      fechaAplicacion: this.form.value.fechaAplicacion || null,
      fechaProxima: this.form.value.fechaProxima,
      aplicada: this.form.value.aplicada || false,
      notas: this.form.value.notas || '',
      veterinarioId: usuario?.role === 'VETERINARIO' ? usuario.id : null
    };

    this.mascotaService.createCalendarioVacuna(calendario).subscribe({
      next: () => {
        Swal.fire({
          title: '¡Vacuna agregada! 💉',
          icon: 'success',
          timer: 1500,
          showConfirmButton: false
        });
        this.showForm = false;
        this.loadCalendario(this.mascota.id);
      },
      error: () => {
        Swal.fire('Error', 'No se pudo agregar la vacuna.', 'error');
      }
    });
  }

  aplicarVacuna(cv: CalendarioVacuna): void {
    Swal.fire({
      title: 'Marcar como aplicada',
      text: `¿Confirmar que se aplicó la vacuna ${cv.vacunaNombre}?`,
      icon: 'question',
      showCancelButton: true,
      confirmButtonColor: '#2E8B57',
      cancelButtonColor: '#999',
      confirmButtonText: 'Sí, confirmar',
      cancelButtonText: 'Cancelar'
    }).then(result => {
      if (result.isConfirmed) {
        const updated: any = {
          ...cv,
          aplicada: true,
          fechaAplicacion: new Date().toISOString().split('T')[0]
        };
        this.mascotaService.aplicarVacuna(cv.id, updated).subscribe({
          next: () => {
            Swal.fire({ title: '✅ Vacuna aplicada', icon: 'success', timer: 1500, showConfirmButton: false });
            this.loadCalendario(this.mascota.id);
          },
          error: () => Swal.fire('Error', 'No se pudo actualizar', 'error')
        });
      }
    });
  }

  getPendientes(): CalendarioVacuna[] {
    return this.calendarioVacunas.filter(c => !c.aplicada);
  }

  getAplicadas(): CalendarioVacuna[] {
    return this.calendarioVacunas.filter(c => c.aplicada);
  }
}
