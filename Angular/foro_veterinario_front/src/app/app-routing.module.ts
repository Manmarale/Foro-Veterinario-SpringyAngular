import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { authGuard } from './auth/helpers/auth.guard';

const routes: Routes = [

  {
    path: 'admin',
    loadChildren: () => import( './admin/admin.module').then(m => m.AdminModule),   //cargando de forma lazy 
    canActivate: [authGuard]
  },
  {
    path: 'mascotas',
    loadChildren: () => import('./mascotas/mascotas.module').then(m => m.MascotasModule),
    canActivate: [authGuard]
  },
  {
    path: '',
    loadChildren: () => import( './home/home.module').then(m => m.HomeModule)   //cargando de forma lazy 
  },
  {
    path: 'auth',
    loadChildren: () => import( './auth/auth.module').then(m => m.AuthModule)   //cargando de forma lazy 
  }

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
