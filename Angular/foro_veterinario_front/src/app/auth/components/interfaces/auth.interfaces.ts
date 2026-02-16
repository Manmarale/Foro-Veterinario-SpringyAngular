export interface SolicitudAutenticacion {
    email?: string;
    password?: string;
}

export interface RespuestaAutenticacion {
    token: string;
    usuario: Profile;
}

export interface Profile{
    nombre: string;
    email : string;
    password : string;
    role: 'USER' | 'ADMIN' ;
}