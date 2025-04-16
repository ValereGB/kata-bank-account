import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Account } from '../models/account.model';

@Injectable({
  providedIn: 'root'
})
export class AccountService {
  private apiUrl = 'http://localhost:8080/api/accounts';

  constructor(private http: HttpClient) { }

  getAccounts(): Observable<Account[]> {
    return this.http.get<Account[]>(this.apiUrl);
  }

  getAccount(accountNumber: string): Observable<Account> {
    return this.http.get<Account>(`${this.apiUrl}/${accountNumber}`);
  }

  createAccount(accountNumber: string, owner: string): Observable<Account> {
    const params = new HttpParams()
      .set('accountNumber', accountNumber)
      .set('owner', owner);
    return this.http.post<Account>(this.apiUrl, null, { params });
  }

  deleteAccount(accountNumber: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${accountNumber}`);
  }

  deposit(accountNumber: string, amount: number): Observable<void> {
    const params = new HttpParams().set('amount', amount.toString());
    return this.http.post<void>(`${this.apiUrl}/${accountNumber}/deposit`, null, { params });
  }

  withdraw(accountNumber: string, amount: number): Observable<void> {
    const params = new HttpParams().set('amount', amount.toString());
    return this.http.post<void>(`${this.apiUrl}/${accountNumber}/withdraw`, null, { params });
  }
} 