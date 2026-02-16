import { Component, OnInit } from '@angular/core';
import { MascotaService } from '../../services/mascota.service';
import { Mascota } from 'src/app/interfaces/mascota.interface';
import { AuthService } from 'src/app/auth/services/auth.service';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-mascotas-list',
  templateUrl: './mascotas-list.component.html',
  styleUrls: []
})
export class MascotasListComponent implements OnInit {

  mascotas: Mascota[] = [];

  constructor(
    private mascotaService: MascotaService,
    private authService: AuthService,
    private router: Router
  ) { }

  ngOnInit(): void {
    const usuario = this.authService.usuario;
    if (usuario) {
      this.mascotaService.findByPropietario(usuario.id)
        .subscribe(mascotas => {
          this.mascotas = mascotas;
        });
    }
  }

  deleteMascota(mascota: Mascota): void {
    Swal.fire({
      title: '¿Está seguro de eliminar esta mascota?',
      text: '¡Se eliminará el registro de la mascota!',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#2E8B57',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Sí, eliminar',
      cancelButtonText: 'Cancelar'
    }).then((result) => {
      if (result.isConfirmed) {
        this.mascotaService.delete(mascota).subscribe({
          next: () => {
            this.mascotas = this.mascotas.filter(m => m.id !== mascota.id);
            Swal.fire({
              title: 'Eliminada',
              text: 'Mascota eliminada con éxito.',
              icon: 'success',
              timer: 2000,
              showConfirmButton: false
            });
          },
          error: (error) => {
            Swal.fire({
              title: 'Error',
              text: 'Hubo un problema al eliminar la mascota.',
              icon: 'error',
              timer: 2000,
              showConfirmButton: false
            });
          }
        });
      }
    });
  }
}
