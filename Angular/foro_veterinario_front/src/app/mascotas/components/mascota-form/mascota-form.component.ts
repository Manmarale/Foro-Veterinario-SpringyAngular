import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { MascotaService } from '../../services/mascota.service';
import { AuthService } from 'src/app/auth/services/auth.service';
import { Mascota } from 'src/app/interfaces/mascota.interface';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-mascota-form',
  templateUrl: './mascota-form.component.html',
  styles: []
})
export class MascotaFormComponent implements OnInit {

  form!: FormGroup;
  editMode = false;
  mascotaId?: number;
  especies = ['Perro', 'Gato', 'Ave', 'Reptil', 'Roedor', 'Pez', 'Otro'];
  sexos = ['Macho', 'Hembra'];
  selectedFile: File | null = null;

  constructor(
    private fb: FormBuilder,
    private mascotaService: MascotaService,
    private authService: AuthService,
    private router: Router,
    private route: ActivatedRoute
  ) { }

  ngOnInit(): void {
    this.form = this.fb.group({
      nombre:          ['', [Validators.required, Validators.maxLength(100)]],
      especie:         ['', [Validators.required]],
      raza:            ['', [Validators.maxLength(100)]],
      fechaNacimiento: [''],
      sexo:            ['', [Validators.required]],
      pesoKg:          [null, [Validators.min(0)]],
      observaciones:   ['', [Validators.maxLength(1000)]],
    });

    this.route.params.subscribe(params => {
      if (params['id']) {
        this.editMode = true;
        this.mascotaId = +params['id'];
        this.loadMascota(this.mascotaId);
      }
    });
  }

  loadMascota(id: number): void {
    this.mascotaService.get(id).subscribe(mascota => {
      this.form.patchValue({
        nombre: mascota.nombre,
        especie: mascota.especie,
        raza: mascota.raza,
        fechaNacimiento: mascota.fechaNacimiento,
        sexo: mascota.sexo,
        pesoKg: mascota.pesoKg,
        observaciones: mascota.observaciones,
      });
    });
  }

  onFileSelected(event: any): void {
    this.selectedFile = event.target.files[0];
  }

  save(): void {
    if (this.form.invalid) return;

    const usuario = this.authService.usuario;
    if (!usuario) return;

    const mascota: any = {
      ...this.form.value,
      propietarioId: usuario.id,
      activo: true
    };

    if (this.selectedFile) {
      const formData = new FormData();
      formData.append('file', this.selectedFile);
      this.mascotaService.uploadFile(formData).subscribe({
        next: (res: any) => {
          mascota.foto = res.path;
          this.saveToApi(mascota);
        },
        error: () => {
          Swal.fire('Error', 'No se pudo subir la imagen', 'error');
        }
      });
    } else {
      this.saveToApi(mascota);
    }
  }

  private saveToApi(mascota: any): void {
    if (this.editMode && this.mascotaId) {
      this.mascotaService.update(this.mascotaId, mascota).subscribe({
        next: () => {
          Swal.fire({
            title: '¡Mascota actualizada!',
            text: 'Los datos se guardaron correctamente.',
            icon: 'success',
            timer: 2000,
            showConfirmButton: false
          });
          this.router.navigate(['/mascotas']);
        },
        error: (err) => {
          Swal.fire('Error', 'No se pudo actualizar la mascota.', 'error');
        }
      });
    } else {
      this.mascotaService.create(mascota).subscribe({
        next: () => {
          Swal.fire({
            title: '¡Mascota registrada!',
            text: 'Tu mascota fue registrada exitosamente.',
            icon: 'success',
            timer: 2000,
            showConfirmButton: false
          });
          this.router.navigate(['/mascotas']);
        },
        error: (err) => {
          Swal.fire('Error', 'No se pudo registrar la mascota.', 'error');
        }
      });
    }
  }
}
