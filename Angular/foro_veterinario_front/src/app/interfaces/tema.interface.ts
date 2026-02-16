export interface TemaPage {
    content:          Tema[];
    pageable:         Pageable;
    last:             boolean;
    totalPages:       number;
    totalElements:    number;
    size:             number;
    number:           number;
    sort:             Sort;
    first:            boolean;
    numberOfElements: number;
    empty:            boolean;
}

export interface Tema {
    id:            number;
    titulo:        string;
    mensaje:       string;
    genero:        Genero;
    usuarioId:     number;
    usuarioNombre: string;
    filePerfil:    string | null;
    filePerfilRespuesta:   string | null;
    createdAt:     Date;
    updatedAt:     Date | null;
    activo:        boolean;
    respuestas:    Respuesta[];
}

export enum Genero {
    ConsultaGeneral = "CONSULTA_GENERAL",
    SaludAnimal = "SALUD_ANIMAL",
    Nutricion = "NUTRICION",
    Vacunacion = "VACUNACION",
    Cirugia = "CIRUGIA",
    Dermatologia = "DERMATOLOGIA",
    Emergencias = "EMERGENCIAS",
    Comportamiento = "COMPORTAMIENTO",
    Adopcion = "ADOPCION",
    CuidadosBasicos = "CUIDADOS_BASICOS",
    EnfermedadesInfecciosas = "ENFERMEDADES_INFECCIOSAS",
    Parasitologia = "PARASITOLOGIA",
    Odontologia = "ODONTOLOGIA",
    Oftalmologia = "OFTALMOLOGIA",
    Cardiologia = "CARDIOLOGIA",
    Reproduccion = "REPRODUCCION",
    AnimalesExoticos = "ANIMALES_EXOTICOS",
    Equinos = "EQUINOS",
    Bovinos = "BOVINOS",
    Felinos = "FELINOS",
    Caninos = "CANINOS",
    Aves = "AVES",
    LegislacionAnimal = "LEGISLACION_ANIMAL",
}

export interface Respuesta {
    id:               number;
    mensajeRespuesta: string;
    temaId:           number;
    usuarioId:        number;
    usuarioNombre:    string;
    filePerfilRespuesta: string;
    activo:           boolean;
    createdAt:        Date;
    updatedAt:        Date | null;
}

export interface RespuestaPage {
    content:          Respuesta[];
    pageable:         Pageable;
    last:             boolean;
    totalPages:       number;
    totalElements:    number;
    size:             number;
    number:           number;
    sort:             Sort;
    first:            boolean;
    numberOfElements: number;
    empty:            boolean;
}

export interface Pageable {
    pageNumber: number;
    pageSize:   number;
    sort:       Sort;
    offset:     number;
    paged:      boolean;
    unpaged:    boolean;
}

export interface Sort {
    empty:    boolean;
    sorted:   boolean;
    unsorted: boolean;
}
