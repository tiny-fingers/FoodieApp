import {Injectable} from '@angular/core';
import {BehaviorSubject} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private loggedIn = new BehaviorSubject<boolean>(false);
  private token = new BehaviorSubject<string>('');

  loggedIn$ = this.loggedIn.asObservable();

  constructor(private http: HttpClient, private router: Router) {
  }
  signup(username: string, password: string,
        options?: {
          redirectUri?: string
        }) {
    this.http.post<void>(`${environment.SERVER_URL}/signup`, {username, password})
      .subscribe(
      {
        next: data => {
          if (options?.redirectUri) {
            void this.router.navigate([options.redirectUri])
          } else {
            void this.router.navigate(['/login'])
          }
        },
        error: err => {
          this.loggedIn.error(err.message);
        }
      }
    )
  }

  login(username: string, password: string,
        options?: {
          redirectUri?: string
        }) {
    this.http.post<{ token: string }>(`${environment.SERVER_URL}/login`, {username, password})
      .subscribe(
      {
        next: data => {
          console.log("token " + data.token)
          this.loggedIn.next(true);
          this.token.next(data.token);
          if (options?.redirectUri) {
            void this.router.navigate([options.redirectUri])
          }
        },
        error: err => {
          alert("Invalid credentials. Please try again")
          void this.router.navigate(['/login'])
        }
      }
    )
  }

  logout() {
    this.token.next('');
    this.loggedIn.next(false);
  }

  isLoggedIn(): boolean {
    return this.loggedIn.getValue();
  }

  getLoginToken() {
    return this.token.getValue();
  }
}
