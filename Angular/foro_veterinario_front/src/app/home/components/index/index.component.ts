import { Component, OnInit, ChangeDetectorRef} from '@angular/core';
import { HomeService } from '../../services/home.service';
import { Tema } from 'src/app/interfaces/tema.interface';
import { Usuario } from 'src/app/interfaces/usuairo.interface';
import { AuthService } from '../../../auth/services/auth.service';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';

import Swiper, { Autoplay, Navigation } from 'swiper';
Swiper.use([Autoplay, Navigation]);

@Component({
  selector: 'app-index',
  templateUrl: './index.component.html',
  styleUrls: ['./index.component.css']
})
export class IndexComponent implements OnInit{

  lastTemas?: Tema[];
  private swiper?: Swiper;
  //usuario: Usuario;

  constructor(
    private homeService: HomeService,
    private authService:AuthService,
    private router:Router,
  ){}

  ngOnInit(): void {
    this.homeService.getLastTemas()
    .subscribe(lastTemas => {
      this.lastTemas = lastTemas;
    });

     setTimeout(() => this.initSwiper(), 300);
  }

  ngAfterViewInit(): void {
      // Reintenta si por algún motivo no estaba listo el DOM
      setTimeout(() => this.initSwiper(), 600);
    }

  navigateToNewTema(): void {
    if (this.authService.isAuthenticated()) {
      this.router.navigate(['/tema/home/new']);
    } else {
      this.alertaSwal("error", 'Necesita estar registrado para crear un tema');
      this.router.navigate(['/auth/login']);
    }
  }

  alertaSwal = (icon: any, title: any) =>{
    Swal.fire({
      position: "center",
      icon: icon ,
      title: title,
      showConfirmButton: false,
      timer: 2500
    });
  }

 private initSwiper(): void {
    if (!this.lastTemas?.length) return;
    if (this.swiper) return; // evita duplicar

    this.swiper = new Swiper('.mySwiper', {
      modules: [Autoplay, Navigation], 
      slidesPerView: 5,
      spaceBetween: 20,
      loop: true,
      speed: 1800,
      grabCursor: true,
      autoplay: {
        delay: 2500,
        disableOnInteraction: false,
      },
      navigation: {
        nextEl: '.swiper-button-next',
        prevEl: '.swiper-button-prev',
      },
      breakpoints: {
        0: {
          slidesPerView: 1, // Celulares
          spaceBetween: 10,
        },
        768: {
          slidesPerView: 2, // Tablets
          spaceBetween: 15,
        },
        1024: {
          slidesPerView: 3, // Laptops medianas
          spaceBetween: 20,
        },
        1440: {
          slidesPerView: 5, // Escritorio grande
          spaceBetween: 25,
        },
      },
    });
  }



}
