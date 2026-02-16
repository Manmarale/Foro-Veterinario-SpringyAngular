import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { IndexComponent } from './components/index/index.component';
import { TemasComponent } from './components/temas/temas.component';
import { TemaComponent } from './components/tema/tema.component';
import { LayoutComponent } from './components/layout/layout.component';
import { FormularioTemaComponent } from './components/formulario-tema/formulario-tema.component';
import { autenticacionGuard } from '../auth/helpers/autenticacion.guard';



const routes: Routes = [
{
  path:'',
  component:LayoutComponent,
  children:[
    {
      path: '',
      component: IndexComponent
    },
    {
      path: 'temas',
      component: TemasComponent
    },
    {
      path: 'tema/:id',
      component: TemaComponent
    },
    {
      path: 'tema/home/new',
      component: FormularioTemaComponent
    },
    {
      path: 'tema/:id/edit', 
      component: FormularioTemaComponent, 
      canActivate: [autenticacionGuard]
    },
  ]
}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class HomeRoutingModule { }
