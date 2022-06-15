import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class LoginService {
  constructor(private http: HttpClient) {}

  usernameExist(username: string): Observable<any> {
    let headers = {
      'Content-Type': 'application/json',
    };

    return this.http.post<string>('/api/noUsernameFound', username, {
      headers: headers,
    });
  }

  login(user:any):Observable<any> {

    let headers = {
      'Content-Type': 'application/json',
    };

    return this.http.post<string>('/api/login', user, {
      headers: headers,
    });
    
  }

  changePassword(data:any){
    
    let headers = {
      'Content-Type': 'application/json',
       'auth' : `Bearer ${localStorage.getItem('token')}`
    };
     
    return this.http.put<string>('/api/changePassword', data, {
      headers: headers,
    });

  }

}
