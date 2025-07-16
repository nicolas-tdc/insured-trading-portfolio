import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { AuthService } from './auth.service';
import { LoginRequest, RegisterRequest, JwtResponse, User } from '../model';
import { provideHttpClient } from '@angular/common/http';

describe('AuthService', () => {
  let service: AuthService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        AuthService,
        provideHttpClient(),
        provideHttpClientTesting(),
      ],
    });

    service = TestBed.inject(AuthService);
    httpMock = TestBed.inject(HttpTestingController);

    localStorage.clear();
  });

  afterEach(() => {
    httpMock.verify();
    localStorage.clear();
  });

  describe('#register', () => {
    it('should POST register request', (done: DoneFn) => {
      const request: RegisterRequest = {
        firstName: 'John',
        lastName: 'Doe',
        email: 'john@example.com',
        password: 'password123',
      };

      const mockResponse = { message: 'User registered successfully' };

      service.register(request).subscribe(res => {
        expect(res).toEqual(mockResponse);
        done();
      });

      const req = httpMock.expectOne('/api/auth/signup');
      expect(req.request.method).toBe('POST');
      req.flush(mockResponse);
    });
  });

  describe('#login', () => {
    it('should POST login credentials and save token and user', (done: DoneFn) => {
      const credentials: LoginRequest = {
        email: 'john@example.com',
        password: 'password123',
      };

      const mockUser: User = {
        id: '1',
        email: 'john@example.com',
        roles: ['USER'],
      };

      const mockResponse: JwtResponse = {
        token: 'jwt-token',
        type: 'Bearer',
        user: mockUser,
      };

      spyOn(service, 'saveToken').and.callThrough();
      spyOn(service, 'saveUser').and.callThrough();

      service.login(credentials).subscribe(res => {
        expect(res).toEqual(mockResponse);
        expect(service.saveToken).toHaveBeenCalledWith(mockResponse.token);
        expect(service.saveUser).toHaveBeenCalledWith(mockResponse.user);
        done();
      });

      const req = httpMock.expectOne('/api/auth/signin');
      expect(req.request.method).toBe('POST');
      req.flush(mockResponse);
    });
  });

  describe('#logout', () => {
    it('should clear auth-token and user from localStorage', () => {
      localStorage.setItem('auth-token', 'token');
      localStorage.setItem('user', JSON.stringify({ id: '1' }));

      service.logout();

      expect(localStorage.getItem('auth-token')).toBeNull();
      expect(localStorage.getItem('user')).toBeNull();
    });
  });

  describe('#saveToken and #getToken', () => {
    it('should save and retrieve token from localStorage', () => {
      service.saveToken('test-token');
      expect(localStorage.getItem('auth-token')).toBe('test-token');
      expect(service.getToken()).toBe('test-token');
    });
  });

  describe('#saveUser', () => {
    it('should save user to localStorage and update authUserResource', () => {
      const user: User = { id: '1', email: 'test@test.com', roles: ['ADMIN'] };

      service.saveUser(user);

      expect(localStorage.getItem('user')).toBe(JSON.stringify(user));
      expect(service.authUser()).toEqual(user);
    });
  });

  describe('#isLoggedIn', () => {
    it('should return true if token exists', () => {
      localStorage.setItem('auth-token', 'token');
      expect(service.isLoggedIn()).toBeTrue();
    });

    it('should return false if no token', () => {
      expect(service.isLoggedIn()).toBeFalse();
    });
  });

  describe('#clearAuthUser', () => {
    it('should clear authUserResource', () => {
      const setSpy = spyOn<any>(service['authUserResource'], 'set');
      service.clearAuthUser();
      expect(setSpy).toHaveBeenCalledWith(null);
    });
  });

  describe('authUser computed signal', () => {
    it('should return null when no user in localStorage', () => {
      expect(service.authUser()).toBeNull();
    });

    it('should return user when user saved', () => {
      const user: User = { id: '1', email: 'a@b.com', roles: [] };
      localStorage.setItem('user', JSON.stringify(user));
      service.saveUser(user);
      expect(service.authUser()).toEqual(user);
    });
  });
});
