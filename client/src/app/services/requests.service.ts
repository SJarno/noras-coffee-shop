import { Injectable } from '@angular/core';
import { HttpClient, HttpParams} from '@angular/common/http';
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

  /* Update username. Consider moving this to own service: */
  updateUsername(newUsername: any, oldUsername: any, ): Observable<any> {
    let params = new HttpParams();
    params.set("newUsername", newUsername);
    params.set("oldUsername", oldUsername);
    console.log("Params: ");
    console.log(params);
    return this.http.put(`${this.url}update-username/${oldUsername}/${newUsername}`, {
      observe: 'response'
    });
  }
}
