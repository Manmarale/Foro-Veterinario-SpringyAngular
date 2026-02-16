import { Component } from '@angular/core';
import { AuthService } from 'src/app/auth/services/auth.service';

@Component({
  selector: 'app-layout',
  templateUrl: './layout.component.html',
  styleUrls: []
})
export class LayoutComponent {
  

  constructor(
    private authService: AuthService
  ){}


  get usuario(){
    return this.authService.usuario;
  }


  logout(){
    this.authService.logout();
  }

}
