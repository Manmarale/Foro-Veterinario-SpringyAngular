import { Component, OnInit } from '@angular/core';
import { UsuarioService } from '../services/usuario.service';
import { Usuario, UsuarioPage } from '../../../interfaces/usuairo.interface';
import { Router } from '@angular/router';
import { PageEvent } from '@angular/material/paginator';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-usuario-list',
  templateUrl: './usuario-list.component.html',
  styleUrls: ['./usuario-list.component.css']
})
export class UsuarioListComponent implements OnInit {

  usuarioPage?: UsuarioPage;
  displayedColums= ['id','titulo','email','password','createdAt','estado','actions']


  constructor(
    private usuarioService: UsuarioService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.loadUsuario();
  }

  loadUsuario() {
    this.usuarioService.paginate()
      .subscribe(usuarioPage => {
        this.usuarioPage = usuarioPage;
      });
  }


  deleteUsuario(usuario: Usuario) {
      Swal.fire({
        title: '¿Está seguro de eliminar el usuario?',
        text: "¡No podrá recuperar el usuario!",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Sí, eliminar',
        cancelButtonText: 'Cancelar'
      }).then((result) => {
        if (result.isConfirmed) {
          
          this.usuarioService.delete(usuario).subscribe({
            next: () => {
              this.loadUsuario();
              Swal.fire({
                title: 'Eliminado',
                text: 'Usuario eliminado con éxito.',
                icon: 'success',
                timer: 2000,
                showConfirmButton: false
              });
            },
            error: () => {
              Swal.fire({
                title: 'Error',
                text: 'Hubo un problema al eliminar el usuario.',
                icon: 'error',
                timer: 2000,
                showConfirmButton: false
              });
            }
          });
        }
      });
    }
  

 

  

  paginateUsuario(event: PageEvent){
    const { pageIndex, pageSize } = event;
    
    this.usuarioService.paginate(pageSize, pageIndex)
      .subscribe(usuarioPage => {
        this.usuarioPage = usuarioPage;
      });
  }




}
