import { Component, OnInit } from '@angular/core';
import { HomeService } from '../../services/home.service';
import { Tema } from 'src/app/interfaces/tema.interface';
import { TemaPage } from '../../../interfaces/tema.interface';

@Component({
  selector: 'app-temas',
  templateUrl: './temas.component.html',
  styleUrls: ['./temas.component.css']
})
export class TemasComponent implements OnInit{

  temas: Tema[] = [];
  page: number = 0;
  last: boolean = false;
  liked: boolean = false; // Nueva propiedad para controlar el estado del "like"

  constructor(
    private homeService:HomeService
  ){}

  ngOnInit(): void {
    this.homeService.paginate()
      .subscribe(temaPage =>{
        this.temas = temaPage.content;
        this.page = temaPage.number;
        this.last = temaPage.last;
      });
  }
  
  loadMoreTemas(){
    //console.log('scrolled')
    if(!this.last){
      this.homeService.paginate( this.page + 1)
      .subscribe(temaPage =>{
        this.temas.push(...temaPage.content);
        this.page = temaPage.number;
        this.last = temaPage.last;
      });
    }
  }

  toggleLike() { // Nuevo método para alternar el estado del "like"
    this.liked = !this.liked;
  }

}
