import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ComponentProcessingService {
  constructor(private http: HttpClient) {}

  processDetail(processDetailData: any): Observable<any> {
    let headers = {
      'Content-Type': 'application/json',
      auth: `Bearer ${localStorage.getItem('token')}`,
    };

    return this.http.post<any>(
      `/component/componentprocessingmodule/ProcessDetail`,
      processDetailData,
      {
        headers: headers,
      }
    );
  }

  getProcessingDetails(): Observable<any[]> {
    let headers = {
      'Content-Type': 'application/json',
      auth: `Bearer ${localStorage.getItem('token')}`,
    };

    return this.http.get<any[]>(
      `/component/componentprocessingmodule/ProcessResponses`,
      {
        headers: headers,
      }
    );
  }

  trackRequest(requestId: string): Observable<any> {
    const headers = {
      'Content-Type': 'application/json',
    };

    return this.http.get<any>(
      `/component/componentprocessingmodule/TrackRequest/${requestId}`,
      { headers: headers }
    );
  }

  getAllRequests() {
    let headers = {
      'Content-Type': 'application/json',
      auth: `Bearer ${localStorage.getItem('token')}`,
    };

    return this.http.get<any[]>(
      `/component/componentprocessingmodule/ProcessRequests`,
      {
        headers: headers,
      }
    );
  }

  deleteRequest(requestId: any, creditCardNumber: any):Observable<any> {
    
    let headers = {
      'Content-Type': 'application/json',
      auth: `Bearer ${localStorage.getItem('token')}`,
    };

    const body  = {
      requestId,
      creditCardNumber
    }

    return this.http.post<any>(`/component/componentprocessingmodule/CancelRequest` , body , {
      'headers' : headers
    })

  }

  CompleteProcessing(data:any):Observable<any>{
    let headers = {
      'Content-Type': 'application/json',
      auth: `Bearer ${localStorage.getItem('token')}`,
    };

    return this.http.post<any>(`/component/componentprocessingmodule/CompleteProcessing` , data , {
      headers
    })

  }
}
