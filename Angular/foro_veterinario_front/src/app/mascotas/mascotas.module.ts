import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { MascotasRoutingModule } from './mascotas-routing.module';
import { MaterialModule } from '../material/material.module';
import { SharedModule } from '../shared/shared.module';
import { MascotasLayoutComponent } from './components/layout/mascotas-layout.component';
import { MascotasListComponent } from './components/mascotas-list/mascotas-list.component';
import { MascotaFormComponent } from './components/mascota-form/mascota-form.component';
import { MascotaDetailComponent } from './components/mascota-detail/mascota-detail.component';
import { CalendarioVacunaComponent } from './components/calendario-vacuna/calendario-vacuna.component';

@NgModule({
  declarations: [
    MascotasLayoutComponent,
    MascotasListComponent,
    MascotaFormComponent,
    MascotaDetailComponent,
    CalendarioVacunaComponent,
  ],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MascotasRoutingModule,
    MaterialModule,
    SharedModule,
  ]
})
export class MascotasModule { }
