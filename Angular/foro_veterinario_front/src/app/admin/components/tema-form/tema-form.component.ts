import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { TemaService } from '../services/tema.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Tema } from '../../../interfaces/tema.interface';
import { AuthService } from 'src/app/auth/services/auth.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-tema-form',
  templateUrl: './tema-form.component.html',
  styleUrls: ['./tema-form.component.css']
})
export class TemaFormComponent implements OnInit {

  errors: string[] = [];
  tema?: Tema;
  form?: FormGroup;
  usuarioId?: number;


  constructor(
    private fb: FormBuilder,
    private temaService: TemaService,
    private router: Router,
    private route: ActivatedRoute,
    private authService: AuthService
  ) { }

  ngOnInit(): void {

    const perfil = this.authService.usuario;

    if(perfil){
      this.usuarioId = perfil.id;
      //console.log('perfilid',perfil.id);
      //console.log('usurioid', this.usuarioId);
      this.initializeForm();
    } else {
      // Manejar el caso en el que no se puede obtener el perfil del usuario
      this.alertaSwal("warning", `Usuario no autenticado`);
      //console.error('Error: Usuario no autenticado');
    }
  }
    
  initializeForm() {

    const temaId = this.route.snapshot.paramMap.get('id');

    if (temaId) {

      this.temaService.get(parseInt(temaId))
        .subscribe(tema => {
          //console.log('tema', tema);

          this.tema = tema;

          this.form = this.fb.group({
            titulo: [tema.titulo, [Validators.required, Validators.minLength(3), Validators.maxLength(100)]],
            mensaje: [tema.mensaje, [Validators.required, Validators.minLength(5), Validators.maxLength(5000), Validators.pattern(/^[A-Za-zÁÉÍÓÚáéíóúÜüÑñ0-9.,\-\/\?\[\]\{\}\s]+$/)]],
            genero: [tema.genero, [Validators.required]],
            usuarioId: [tema.usuarioId, [Validators.required]]
          });
        });

    } else {

      this.form = this.fb.group({
        titulo: [, [Validators.required, Validators.minLength(3), Validators.maxLength(100)]],
        mensaje: [, [Validators.required, Validators.minLength(5), Validators.maxLength(5000), Validators.pattern(/^[A-Za-zÁÉÍÓÚáéíóúÜüÑñ0-9.,\-\/\?\[\]\{\}\s]+$/)]],
        genero: [, [Validators.required]],
        usuarioId: [this.usuarioId, [Validators.required]]
      });

    }
  }


  controlHasError(control: string, error: string) {
    return this.form!.controls[control].hasError(error) && this.form!.controls[control].touched;
  }

  save() {
    if (this.form!.invalid) {
      this.form?.markAllAsTouched();
      return;
    }

    let tema = this.form!.value;
    let request;


    if (this.tema) {
      request = this.temaService.update(this.tema.id, tema);
      this.alertaSwal("success", `Tema Actualizado`);
    } else {
      request = this.temaService.create(tema);
      this.alertaSwal("success", `Tema Creado`);
    }

    request
      .subscribe({
        next: tema => {
          this.router.navigate(['/admin/tema']);
        },
        error: error => {
          if (error.error.status === 400) {
            this.errors.push(error.error.detail);
          } else if (error.error.status === 422) {
            this.errors.push(...error.error.errors)
          }
        }
      });
  }


  alertaSwal = (icon: any, title: any) =>{
    Swal.fire({
      position: "center",
      icon: icon ,
      title: title,
      showConfirmButton: false,
      timer: 2500
    });
  }


}
