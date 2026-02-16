import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable, OnInit } from '@angular/core';
import { Tema, TemaPage } from '../../../interfaces/tema.interface';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class TemaService implements OnInit {

  constructor(
    private http:HttpClient
  ) {}


  ngOnInit(): void {
    
  }


  paginate(size: number = 5, page: number = 0){
    let params = new HttpParams();
    params = params.append('size', size);
    params = params.append('page', page);
    params = params.append('sort', 'createdAt,desc');

    return this.http.get<TemaPage>(`${environment.apiBase}/admin/topicos`, {params});
  }

  create(tema: Tema){
    return this.http.post<Tema>(`${environment.apiBase}/topicos`, tema);
  }

  update(id: number, tema: Tema){
    return this.http.put<Tema>(`${environment.apiBase}/topicos/${id}`, tema);
  }

  get(id:number){
    return this.http.get<Tema>(`${environment.apiBase}/topicos/${id}`);
  }

  delete(tema: Tema){
    return this.http.delete(`${environment.apiBase}/topicos/${tema.id}`);
  }

}
