import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/auth/services/auth.service';

@Component({
  selector: 'app-layout',
  templateUrl: './layout.component.html',
  styleUrls: ['./layout.component.css']
})
export class LayoutComponent {

  constructor(
    private authService:AuthService,
    private router : Router
  ){}


  get usuario(){
    return this.authService.usuario!;
  }

  logout(){
    this.router.navigate(['']);
    this.authService.logout();
  }


}
