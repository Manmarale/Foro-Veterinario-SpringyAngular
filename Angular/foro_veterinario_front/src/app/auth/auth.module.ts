import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MaterialModule } from '../material/material.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AuthRoutingModule } from './auth-routing.module';
import { LoginComponent } from './components/login/login.component';
import { SingupComponent } from './components/singup/singup.component';
import { LayoutComponent } from './components/layout/layout.component';
import { SharedModule } from "../shared/shared.module";



@NgModule({
  declarations: [
    LoginComponent,
    SingupComponent,
    LayoutComponent
  ],
  imports: [
    CommonModule,
    MaterialModule,
    FormsModule,
    ReactiveFormsModule,
    AuthRoutingModule,
    SharedModule
]
})
export class AuthModule { }
