import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from '../services/auth.service';


export const autenticacionGuard: CanActivateFn = (route, state) => {
  const authService = inject(AuthService);
  const router = inject(Router);


  if (authService.isAuthenticated()) {
    return true;
  }

  alert('Necesitas estar registrado para editar');
  router.navigate(['/auth/login']);
  return false;

  
};