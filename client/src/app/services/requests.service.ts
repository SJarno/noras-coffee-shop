import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RequestsService {
  url: string = environment.baseUrl;
  constructor(private http: HttpClient) { }

  getGreeting(): Observable<any> {
    return this.http.get(this.url + "greet", { observe: 'response' });
  }

  /* Update username. Consider moving this to own service: */
  updateUsername(newUsername: any): Observable<any> {
    return this.http.put<any>(`${this.url}update-username`,
      newUsername, 
      { observe: 'response' });
  }
}
