import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

export interface Alert {
  type: 'success' | 'danger' | 'warning' | 'info';
  message: string;
}

@Injectable({
  providedIn: 'root'
})
export class AlertService {
  private alertSubject = new BehaviorSubject<Alert | null>(null);
  alert$ = this.alertSubject.asObservable();

  show(type: 'success' | 'danger' | 'warning' | 'info', message: string) {
    this.alertSubject.next({ type, message });
    // Auto-hide after 5 seconds
    setTimeout(() => this.clear(), 5000);
  }

  clear() {
    this.alertSubject.next(null);
  }
} 