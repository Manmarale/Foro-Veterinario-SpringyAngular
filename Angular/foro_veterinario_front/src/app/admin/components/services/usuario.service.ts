import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Usuario, UsuarioPage } from '../../../interfaces/usuairo.interface';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {

  constructor(
    private http: HttpClient
  ) {}


  paginate(size: number = 5, page: number = 0){
    let params = new HttpParams();
    params = params.append('size', size);
    params = params.append('page', page);
    params = params.append('sort', 'createdAt,desc');

    return this.http.get<UsuarioPage>(`${environment.apiBase}/admin/usuario`, {params});
  }

  get(id: number){
    return this.http.get<Usuario>(`${environment.apiBase}/admin/usuario/${id}`);
  }

  create(usuario: Usuario){
    return this.http.post<Usuario>(`${environment.apiBase}/admin/usuario`, usuario);
  }

  update(id: number , usuario: Usuario){
    return this.http.put(`${environment.apiBase}/admin/usuario/${id}`, usuario);
  }


  delete(usuario: Usuario){
    return this.http.delete(`${environment.apiBase}/admin/usuario/${usuario.id}`);
  }

  uploadFile(formData: FormData){
    return this.http.post(`${environment.apiBase}/media/upload`, formData);
  }

}
