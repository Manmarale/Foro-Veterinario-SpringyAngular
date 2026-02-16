import { HttpClient, HttpParams} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Respuesta, RespuestaPage } from 'src/app/interfaces/tema.interface';


import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class RespuestaService {

  constructor(
    private http:HttpClient
  ) { }


  paginate( size: number = 5, page: number = 0){
    let params = new HttpParams();
    params = params.append('size', size);
    params = params.append('page', page);
    params = params.append('sort', 'createdAt,desc');
    return this.http.get<RespuestaPage>(`${environment.apiBase}/respuesta`,{params})
  }

  delete(respuesta: Respuesta){
    return this.http.delete(`${environment.apiBase}/respuesta/${respuesta.id}`);
  }

}
