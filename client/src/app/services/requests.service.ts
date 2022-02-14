import { Injectable } from '@angular/core';
import { HttpClient} from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RequestsService {
  url: string = environment.baseUrl;
  constructor(private http: HttpClient) { }

  getGreeting(): Observable<any> {
    return this.http.get(this.url+"greet", { observe: 'response'});
  }
}
