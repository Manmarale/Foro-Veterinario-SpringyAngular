import { Component, OnInit } from '@angular/core';
import { RespuestaService } from '../services/respuesta.service';
import { Respuesta, RespuestaPage } from 'src/app/interfaces/tema.interface';
import { PageEvent } from '@angular/material/paginator';
import Swal from 'sweetalert2';


;


@Component({
  selector: 'app-respuestas-list',
  templateUrl: './respuestas-list.component.html',
  styleUrls: []
})
export class RespuestasListComponent implements OnInit{

  respuestaPage?: RespuestaPage;
  displayedColums = [
    'id','respuesta','tema','usuario','createdAt','activo','actions'
  ]

  respuestas?: Respuesta[];


  constructor(
    private respuestaService: RespuestaService
  ){}

  ngOnInit(): void {
    this.loadRespuesta();
  }

  loadRespuesta(){
    this.respuestaService.paginate()
    .subscribe(respuestaPage =>{
      this.respuestaPage = respuestaPage;
    });
  }

 
  deleteRespuesta(respuesta: Respuesta) {
    Swal.fire({
      title: '¿Está seguro de eliminar la respuesta?',
      text: "¡No podrá recuperar la respuesta!",
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Sí, eliminar',
      cancelButtonText: 'Cancelar'
    }).then((result) => {
      if (result.isConfirmed) {
        
        this.respuestaService.delete(respuesta).subscribe({
          next: () => {
            this.loadRespuesta();
            Swal.fire({
              title: 'Eliminado',
              text: 'Respuesta eliminada con éxito.',
              icon: 'success',
              timer: 2000,
              showConfirmButton: false
            });
          },
          error: () => {
            Swal.fire({
              title: 'Error',
              text: 'Hubo un problema al eliminar la Respuesta.',
              icon: 'error',
              timer: 2000,
              showConfirmButton: false
            });
          }
        });
      }
    });
  }

  paginateRespuesta (event: PageEvent){
    const { pageIndex, pageSize } = event;

    this.respuestaService.paginate(pageSize, pageIndex)
      .subscribe(respuestaPage => {
        this.respuestaPage = respuestaPage;
      });
  }



}
