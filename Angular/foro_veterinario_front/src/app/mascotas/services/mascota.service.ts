import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Mascota, MascotaPage, CalendarioVacuna, Vacuna } from 'src/app/interfaces/mascota.interface';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class MascotaService {

  constructor(
    private http: HttpClient
  ) { }

  paginate(page: number = 0, size: number = 10) {
    let params = new HttpParams();
    params = params.append('size', size);
    params = params.append('page', page);
    params = params.append('sort', 'createdAt,desc');
    return this.http.get<MascotaPage>(`${environment.apiBase}/mascotas`, { params });
  }

  findByPropietario(propietarioId: number) {
    return this.http.get<Mascota[]>(`${environment.apiBase}/mascotas/propietario/${propietarioId}`);
  }

  get(id: number) {
    return this.http.get<Mascota>(`${environment.apiBase}/mascotas/${id}`);
  }

  create(mascota: Mascota) {
    return this.http.post<Mascota>(`${environment.apiBase}/mascotas`, mascota);
  }

  update(id: number, mascota: Mascota) {
    return this.http.put<Mascota>(`${environment.apiBase}/mascotas/${id}`, mascota);
  }

  delete(mascota: Mascota) {
    return this.http.delete(`${environment.apiBase}/mascotas/${mascota.id}`);
  }

  // Vacunas
  getVacunas() {
    return this.http.get<Vacuna[]>(`${environment.apiBase}/vacunas`);
  }

  getVacunasByEspecie(especie: string) {
    return this.http.get<Vacuna[]>(`${environment.apiBase}/vacunas/especie/${especie}`);
  }

  // Calendario de vacunas
  getCalendarioByMascota(mascotaId: number) {
    return this.http.get<CalendarioVacuna[]>(`${environment.apiBase}/calendario-vacunas/mascota/${mascotaId}`);
  }

  getPendientesByMascota(mascotaId: number) {
    return this.http.get<CalendarioVacuna[]>(`${environment.apiBase}/calendario-vacunas/mascota/${mascotaId}/pendientes`);
  }

  createCalendarioVacuna(calendario: CalendarioVacuna) {
    return this.http.post<CalendarioVacuna>(`${environment.apiBase}/calendario-vacunas`, calendario);
  }

  aplicarVacuna(id: number, calendario: CalendarioVacuna) {
    return this.http.put<CalendarioVacuna>(`${environment.apiBase}/calendario-vacunas/${id}/aplicar`, calendario);
  }

  uploadFile(formData: FormData) {
    return this.http.post(`${environment.apiBase}/media/upload`, formData);
  }
}
