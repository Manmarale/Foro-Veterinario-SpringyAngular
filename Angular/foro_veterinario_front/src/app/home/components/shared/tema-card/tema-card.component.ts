import { Component, Input } from '@angular/core';
import { Tema } from '../../../../interfaces/tema.interface';

@Component({
  selector: 'app-tema-card',
  templateUrl: './tema-card.component.html',
  styleUrls: []
})
export class TemaCardComponent {

  @Input() tema!: Tema;

  liked: boolean = false; // Nueva propiedad para controlar el estado del "like"
  toggleLike() { // Nuevo método para alternar el estado del "like"
    this.liked = !this.liked;
  }

}
