import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../../services/auth.service';
import { SingupRequest } from '../../interfaces/auth.interfaces';
import { UsuarioService } from 'src/app/admin/components/services/usuario.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-singup',
  templateUrl: './singup.component.html',
  styleUrls: []
})
export class SingupComponent {

  errors: string [] = [];

  passwordVisible = false;


  form:FormGroup = this.fb.group({
    nombre: [, [ Validators.required]],
    email: [, [Validators.required, Validators.email]],
    password: [,[Validators.required, Validators.minLength(5), Validators.pattern('[a-z0-9-]+')]],
    filePerfil:[null] 
  })

  constructor(
    private fb: FormBuilder,
    private authService:AuthService,
    private usuarioService:UsuarioService,
    private snackBar :MatSnackBar,
    private router: Router
  ){}

  controlHasError(control:string, error: string){
    return this.form.controls[control].hasError(error);
  }

  singup(){
    if(this.form.invalid){
      return;
    }

    const formValues = this.form.value;

    this.authService.singup(formValues)
    .subscribe({
      next: singupRequest => {
        this.authService.autenticacion({
          email: formValues.email,
          password: formValues.password
        })
        .subscribe(profile =>{
/*
          this.snackBar.open(`Bienvenido ${profile.nombre}`, 'Cerrar', { 
            duration: 5000,
            verticalPosition: 'bottom', // cambiar por sweetmessages
            horizontalPosition: 'center'// cambiar por sweetmessages
          });
*/

          this.alertaSwal("success", `Bienvenido ${profile.nombre}`)

          this.router.navigate([''])
        });
      },
      error: error => {
        if(error.error.status === 400){
          this.errors.push(error.error.detail);
        }else if(error.error.status === 422){
          this.errors.push(...error.error.errors)
        }
      }
    })
  }

  uploadFile(event: any, control: string){
    const file = event.target.files[0];

    if(file){
      const formData = new FormData();
      formData.append('file', file);

      this.usuarioService.uploadFile(formData)
      .subscribe((response:any) => {
        this.form!.controls[control].setValue(response.path);
      });
    }
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
