import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterOutlet } from '@angular/router';
import { AlertService } from './services/alert.service';
import { Observable } from 'rxjs';

interface Alert {
  type: 'success' | 'danger' | 'warning' | 'info';
  message: string;
}

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, RouterOutlet],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'bank-app';
  alerts: Alert[] = [];
  alert$: Observable<Alert | null>;

  constructor(private alertService: AlertService) {
    this.alert$ = this.alertService.alert$;
  }

  addAlert(type: Alert['type'], message: string) {
    const alert: Alert = { type, message };
    this.alerts.push(alert);
    setTimeout(() => this.removeAlert(alert), 5000);
  }

  removeAlert(alert: Alert) {
    this.alerts = this.alerts.filter(a => a !== alert);
  }

  clearAlert() {
    this.alertService.clear();
  }
}
