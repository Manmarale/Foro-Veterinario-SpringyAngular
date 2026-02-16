export interface SolicitudAutenticacion {
    email?: string;
    password?: string;
}

export interface RespuestaAutenticacion {
    token: string;
    usuario: Profile;
}

export interface Profile{
    filePerfil : string;
    id:number;
    nombre: string;
    email : string;
    password : string;
    role: 'USER' | 'ADMIN' | 'VETERINARIO' | 'ESTUDIANTE' | 'PROPIETARIO';
   
}

export interface SingupRequest{
    nombre: string;
    email : string;
    password : string;
    role: 'USER' | 'ADMIN' | 'VETERINARIO' | 'ESTUDIANTE' | 'PROPIETARIO';
    filePerfil: string;
}