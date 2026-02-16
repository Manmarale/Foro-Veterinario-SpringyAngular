import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { TemasListComponent } from './components/temas-list/temas-list.component';
import { TemaFormComponent } from './components/tema-form/tema-form.component';
import { UsuarioListComponent } from './components/usuario-list/usuario-list.component';
import { UsuarioFormComponent } from './components/usuario-form/usuario-form.component';
import { LayoutComponent } from './components/layout/layout.component';
import { RespuestasListComponent } from './components/respuestas-list/respuestas-list.component';
import { RespuestaFormComponent } from './components/respuesta-form/respuesta-form.component';


const routes: Routes = [
  {
    path: '',
    component: LayoutComponent,
    children:[
      {
        path: 'tema',
        component: TemasListComponent
      },
      {
        path:'tema/new',
        component: TemaFormComponent
      },
      {
        path:'tema/:id/edit',
        component: TemaFormComponent
      },
      {
        path:'usuarios',
        component: UsuarioListComponent
      },{
        path:'usuario/new',
        component: UsuarioFormComponent
      },
      {
        path:'usuario/:id/edit',
        component: UsuarioFormComponent
      },
      {
        path: 'respuestas',
        component: RespuestasListComponent
      },
      {
        path: 'respuesta/new',
        component: RespuestaFormComponent
      },
      {
        path: 'respuesta/:id/edit',
        component: RespuestaFormComponent
      },
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdminRoutingModule { }
