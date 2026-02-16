import { Pipe, PipeTransform } from '@angular/core';
import { environment } from 'src/environments/environment';

@Pipe({
  name: 'apiImg'
})
export class ApiImgPipe implements PipeTransform {

  transform(value: string): string {
    return `${environment.apiBase}/media/${value}` ;
  }

}
