import { Component } from '@angular/core';
import { SolicitudAutenticacion, Profile } from '../../interfaces/auth.interfaces';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup, NgForm, Validators } from '@angular/forms';
import { AuthService } from '../../services/auth.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: []
})
export class LoginComponent {
  solicitudAutenticacion : SolicitudAutenticacion = {};
  errors: string [] = [];
  passwordVisible = false;

  form:FormGroup = this.fb.group({
      email: [, [Validators.required, Validators.email]],
      password: [,[Validators.required, Validators.minLength(4)]]
    });

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private authService: AuthService,
    private snackBar: MatSnackBar
  ){}

  controlHasError(control:string, error: string){
    return this.form.controls[control].hasError(error);
  }

  login(){
    if(this.form.invalid){
      return;
    }
    this.errors = [];
    this.solicitudAutenticacion = this.form.value; 

    this.authService.autenticacion(this.solicitudAutenticacion)
    .subscribe({
      next: profile =>{
        this.alertaSwal("success", `Bienvenido ${profile.nombre}`);
        this.router.navigate(['']);
      },
      
      error: err => {
        const e = err.error;
        if (typeof e === 'string') {
            this.errors.push(e);
        } else if (e && (e.status === 400 || e.status === 403)) { 
          const mensaje = e.detail || 'ERROR EMAIL O CONTRASEÑA: INCORRECTA!';
          this.errors.push(mensaje);
          this.alertaSwal('error', mensaje);
        } else if (e && e.status === 422 && e.errors) {
          this.errors.push(...e.errors);
        }else{
          this.errors.push('Ocurrió un error inesperado. Intente nuevamente.');
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
