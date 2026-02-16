import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { MascotasLayoutComponent } from './components/layout/mascotas-layout.component';
import { MascotasListComponent } from './components/mascotas-list/mascotas-list.component';
import { MascotaFormComponent } from './components/mascota-form/mascota-form.component';
import { MascotaDetailComponent } from './components/mascota-detail/mascota-detail.component';
import { CalendarioVacunaComponent } from './components/calendario-vacuna/calendario-vacuna.component';

const routes: Routes = [
  {
    path: '',
    component: MascotasLayoutComponent,
    children: [
      { path: '', component: MascotasListComponent },
      { path: 'nueva', component: MascotaFormComponent },
      { path: 'editar/:id', component: MascotaFormComponent },
      { path: ':id', component: MascotaDetailComponent },
      { path: ':id/calendario', component: CalendarioVacunaComponent },
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class MascotasRoutingModule { }
