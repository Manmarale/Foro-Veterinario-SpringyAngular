import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Respuesta, Tema, TemaPage } from 'src/app/interfaces/tema.interface';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class HomeService {

  constructor(
    private http:HttpClient
  ) { }

  getLastTemas(){
    return this.http.get<Tema[]>(`${environment.apiBase}/home/last-temas`);
  }

  paginate( page: number = 0, size: number = 10){
    let params = new HttpParams();
    params = params.append('size', size);
    params = params.append('page', page);
    params = params.append('sort', 'createdAt,desc');

    return this.http.get<TemaPage>(`${environment.apiBase}/home/topicos`, {params});
  }

  get(id : number){
    return this.http.get<Tema>(`${environment.apiBase}/topicos/${id}`);
  }

  createRespuesta(respuesta:Respuesta){
    return this.http.post<Respuesta>(`${environment.apiBase}/respuesta`,respuesta)
  }


  updateRespuesta(id: number, respuesta: Respuesta){
    return this.http.put<Respuesta>(`${environment.apiBase}/respuesta/${id}`, respuesta);
  }
}
