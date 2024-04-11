import {Component, Input} from '@angular/core';
import {NgIf} from "@angular/common";
import {Router} from "@angular/router";
import {AuthService} from "../../services/auth.service";

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [
    NgIf
  ],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css'
})
export class NavbarComponent {
  @Input() isLoggedIn: boolean = false;

  constructor(private authService: AuthService, private router: Router) {
    this.authService.loggedIn$.subscribe(loggedIn => {
      this.isLoggedIn = loggedIn;
    });
  }

  ngOnInit(): void {
    this.authService.loggedIn$.subscribe(loggedIn => {
      this.isLoggedIn = loggedIn;
    });
  }

  ngAfterViewInit() {
    this.authService.loggedIn$.subscribe(loggedIn => {
      this.isLoggedIn = loggedIn;
    });
  }

  goTo(uri: string) {
    void this.router.navigate([uri]);
  }

  login() {
    void this.router.navigate(['login']);
  }

  logout() {
    this.authService.logout();
  }

}
