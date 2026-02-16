export interface MascotaPage {
    content:          Mascota[];
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

export interface Mascota {
    id:              number;
    nombre:          string;
    especie:         string;
    raza:            string;
    fechaNacimiento: string | null;
    sexo:            string;
    pesoKg:          number | null;
    foto:            string | null;
    observaciones:   string | null;
    propietarioId:   number;
    propietarioNombre: string;
    activo:          boolean;
    createdAt:       Date;
    updatedAt:       Date | null;
    calendarioVacunas: CalendarioVacuna[];
}

export interface Vacuna {
    id:            number;
    nombre:        string;
    descripcion:   string;
    especie:       string;
    intervaloDias: number;
    obligatoria:   boolean;
    activo:        boolean;
}

export interface CalendarioVacuna {
    id:                  number;
    mascotaId:           number;
    mascotaNombre:       string;
    vacunaId:            number;
    vacunaNombre:        string;
    fechaAplicacion:     string | null;
    fechaProxima:        string;
    aplicada:            boolean;
    recordatorioEnviado: boolean;
    notas:               string | null;
    veterinarioId:       number | null;
    veterinarioNombre:   string | null;
    propietarioEmail:    string;
    createdAt:           Date;
    updatedAt:           Date | null;
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
