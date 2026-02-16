export interface UsuarioPage {
    content:          Usuario[];
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

export interface Usuario {
    id:         number;
    nombre:     string;
    email:      string;
    password:   string;
    role:       string;
    filePerfil: string;
    activo:     boolean;
    createdAt:  Date;
    updatedAt:  null;
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
