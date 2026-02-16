import { Component, OnInit } from '@angular/core';
import { TemaService } from '../services/tema.service';
import { Tema, TemaPage } from '../../../interfaces/tema.interface';
import { PageEvent } from '@angular/material/paginator';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-temas-list',
  templateUrl: './temas-list.component.html',
  styleUrls: ['./temas-list.component.css']
})
export class TemasListComponent implements OnInit {

  temaPage?: TemaPage;
  displayedColums =[
    'id','mensaje','genero','usuario','createdAt','activo','actions'
  ]

  constructor(
    private temaService: TemaService
  ) { }

  ngOnInit(): void {
    this.loadTemas();
  }

  loadTemas(){
    this.temaService.paginate()
    .subscribe(temaPage => {
      this.temaPage = temaPage;
    });
  }


  deleteTema(tema: Tema) {
    Swal.fire({
      title: '¿Está seguro de eliminar el tema?',
      text: "¡No podrá recuperar el tema!",
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
            this.loadTemas();
            Swal.fire({
              title: 'Eliminado',
              text: 'Tema eliminado con éxito.',
              icon: 'success',
              timer: 2000,
              showConfirmButton: false
            });
          },
          error: () => {
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
  }
  


  paginateTemas (event: PageEvent){
    const { pageIndex, pageSize } = event;

    this.temaService.paginate(pageSize, pageIndex)
      .subscribe(temaPage => {
        this.temaPage = temaPage;
      });
  }


  


}
