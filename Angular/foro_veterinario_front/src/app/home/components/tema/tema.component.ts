import { Component, OnInit } from '@angular/core';
import { HomeService } from '../../services/home.service';
import { Respuesta, Tema } from 'src/app/interfaces/tema.interface';
import { ActivatedRoute, Route, Router } from '@angular/router';
import { TemaService } from '../../../admin/components/services/tema.service';
import { AuthService } from 'src/app/auth/services/auth.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-tema',
  templateUrl: './tema.component.html',
  styleUrls: ['./tema.component.css']
})
export class TemaComponent implements OnInit {

  tema?: Tema;
  respuesta?:Respuesta;
  mostrarRespuestasState: boolean = false;
  usuarioAutenticado: boolean = false;
  form?:FormGroup;
  errors: string[] = [];
  liked: boolean = false; // Nueva propiedad para controlar el estado del "like"




  constructor(
    private homeServices: HomeService,
    private route: ActivatedRoute,
    private temaService: TemaService,
    private router: Router,
    public authService: AuthService,
    private fb: FormBuilder,

  ) { }

  ngOnInit(): void {

    this.initializeForm();

    const temaId = this.route.snapshot.paramMap.get('id');
    //console.log('temaId:', temaId); 


    if (temaId) {
      this.homeServices.get(parseInt(temaId))
        .subscribe(tema => {
          //console.log('tema', tema);
          this.tema = tema;

          // Verificar si el usuario autenticado puede eliminar este tema
          this.usuarioAutenticado = this.authService.usuario?.id === tema.usuarioId;

          this.form?.patchValue({
            temaId: tema.id,
            usuarioId: this.authService.usuario?.id
          })

  
        });
    }
  }

  mostrarRespuestas(): void {
    this.mostrarRespuestasState = !this.mostrarRespuestasState;
  }

  deleteTema(tema: Tema): void {
    if (this.usuarioAutenticado) {
      Swal.fire({
        title: '¿Está seguro de eliminar el tema?',
        text: '¡No podrá recuperar el tema!',
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Sí, eliminar',
        cancelButtonText: 'Cancelar'
      }).then((result) => {
        if (result.isConfirmed) {
          this.temaService.delete(tema).subscribe({
            next: () => {
              console.log('Tema eliminado');
              this.router.navigate(['/tema']);
              Swal.fire({
                title: 'Eliminado',
                text: 'Tema eliminado con éxito.',
                icon: 'success',
                timer: 2000,
                showConfirmButton: false
              });
            },
            error: (error) => {
              console.error('Error al eliminar el tema', error);
              Swal.fire({
                title: 'Error',
                text: 'Hubo un problema al eliminar el tema.',
                icon: 'error',
                timer: 2000,
                showConfirmButton: false
              });
            }
          });
        }
      });
    } else {
      this.alertaSwal("warning",'No tienes permiso para eliminar este tema');
      // Aquí podrías mostrar un mensaje al usuario indicando que no tiene permiso
    }
  }


  

  initializeForm() {
    this.form = this.fb.group({
      mensajeRespuesta:['',[Validators.required]],
      temaId:['',[Validators.required]],
      usuarioId:['',[Validators.required]]
      
    });
  }


  createRespuesta() {

    if (!this.authService.isAuthenticated()) {
      this.alertaSwal("warning", 'Necesita estar registrado para comentar un tema');
      this.router.navigate(['/auth/login']);
    }

    if (this.form!.invalid) {
      this.form?.markAllAsTouched();
      return;
    }

    let respuesta = this.form!.value;
    let request;


    if (this.respuesta) {
      request = this.homeServices.updateRespuesta(this.respuesta.id, respuesta);
      this.alertaSwal("success", `Respuesta Actualizada`);
    } else {
      request = this.homeServices.createRespuesta(respuesta);
      this.alertaSwal("success", `Respuesta Creada`);
    }

    request
      .subscribe({
        next: respuesta => {
          this.router.navigate(['/temas']);
        }
      })
  }


  controlHasError(control: string, error: string) {
    return this.form!.controls[control].hasError(error) && this.form!.controls[control].touched;
  }

  toggleLike() { // Nuevo método para alternar el estado del "like"
    this.liked = !this.liked;
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