import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';
import { UserAccountsService } from './user-accounts.service';
import { Account } from '../model';
import { AuthService } from '../../auth/service';
import { SorterService } from '../../shared/service/sorter.service';
import { accountFieldTypes } from '../model/account-field-types.model';

describe('UserAccountsService', () => {
  let service: UserAccountsService;
  let httpMock: HttpTestingController;

  // Spies for dependencies
  let authServiceSpy: jasmine.SpyObj<AuthService>;
  let sorterServiceSpy: jasmine.SpyObj<SorterService>;

  beforeEach(() => {
    authServiceSpy = jasmine.createSpyObj('AuthService', ['isLoggedIn']);
    sorterServiceSpy = jasmine.createSpyObj('SorterService', ['sortListByField']);

    TestBed.configureTestingModule({
      imports: [],
      providers: [
        UserAccountsService,
        provideHttpClient(),
        provideHttpClientTesting(),
        { provide: AuthService, useValue: authServiceSpy },
        { provide: SorterService, useValue: sorterServiceSpy },
      ],
    });

    service = TestBed.inject(UserAccountsService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify(); // Verify no outstanding requests
  });

  describe('#getUserAccountsList', () => {
    it('should return empty array when user is not logged in', (done: DoneFn) => {
      authServiceSpy.isLoggedIn.and.returnValue(false);

      service.getUserAccountsList().subscribe(accounts => {
        expect(accounts).toEqual([]);
        done();
      });
    });
  });

  describe('userAccounts resource', () => {
    it('should initially load user accounts from resource', (done: DoneFn) => {
      authServiceSpy.isLoggedIn.and.returnValue(true);

      const mockAccounts: Account[] = [
        { id: '1', statusCode: 'ACTIVE', statusDisplayName: 'Active', typeCode: 'CHK', typeDisplayName: 'Checking', accountNumber: '123', balance: 100, currencyCode: 'USD', currencySymbol: '$', currencyFractionDigits: 2, policies: [] }
      ];

      service['userAccountsResource'].set(mockAccounts);
      expect(service.userAccounts()).toEqual(mockAccounts);
      done();
    });

    it('reloadUserAccounts should trigger reload on resource', () => {
      const reloadSpy = spyOn<any>(service['userAccountsResource'], 'reload');
      service.reloadUserAccounts();
      expect(reloadSpy).toHaveBeenCalled();
    });

    it('clearUserAccounts should set resource value to empty array', () => {
      const setSpy = spyOn<any>(service['userAccountsResource'], 'set');
      service.clearUserAccounts();
      expect(setSpy).toHaveBeenCalledWith([]);
    });
  });
});
