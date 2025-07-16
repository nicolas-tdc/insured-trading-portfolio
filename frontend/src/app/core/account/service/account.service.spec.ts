import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { AccountService } from './account.service';
import { Account, AccountRequest } from '../model';
import { AccountType } from '../model/account-type.model';
import { provideHttpClient } from '@angular/common/http';

describe('AccountService', () => {
  let service: AccountService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [],
      providers: [
        AccountService,
        provideHttpClient(),
        provideHttpClientTesting(),
      ],
    });

    service = TestBed.inject(AccountService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify(); // Ensure no outstanding HTTP calls
  });

  describe('#selectAccount and #getSelectedAccountId', () => {
    it('should update and return selected account id', () => {
      service.selectAccount('abc123');
      expect(service.getSelectedAccountId()).toBe('abc123');
    });

    it('should clear selected account id when null is passed', () => {
      service.selectAccount(null);
      expect(service.getSelectedAccountId()).toBeNull();
    });
  });

  describe('#create', () => {
    it('should POST account creation request', (done: DoneFn) => {
      const request: AccountRequest = {
        typeCode: 'SAVINGS',
        currencyCode: 'USD',
      };

      const expectedResponse: Account = {
        id: '123',
        statusCode: 'ACTIVE',
        statusDisplayName: 'Active',
        typeCode: 'SAVINGS',
        typeDisplayName: 'Savings',
        accountNumber: '000111222',
        balance: 1000,
        currencyCode: 'USD',
        currencySymbol: '$',
        currencyFractionDigits: 2,
        policies: [],
      };

      service.create(request).subscribe((res) => {
        expect(res).toEqual(expectedResponse);
        done();
      });

      const req = httpMock.expectOne('/api/account');
      expect(req.request.method).toBe('POST');
      req.flush(expectedResponse);
    });
  });

  describe('#getItem', () => {
    it('should GET an account by ID', (done: DoneFn) => {
      const expectedAccount: Account = {
        id: '456',
        statusCode: 'ACTIVE',
        statusDisplayName: 'Active',
        typeCode: 'CHECKING',
        typeDisplayName: 'Checking',
        accountNumber: '987654321',
        balance: 2000,
        currencyCode: 'USD',
        currencySymbol: '$',
        currencyFractionDigits: 2,
        policies: [],
      };

      service.getItem('456').subscribe((account) => {
        expect(account).toEqual(expectedAccount);
        done();
      });

      const req = httpMock.expectOne('/api/account/456');
      expect(req.request.method).toBe('GET');
      req.flush(expectedAccount);
    });
  });

  describe('#getTypes', () => {
    it('should GET account types', (done: DoneFn) => {
      const types: AccountType[] = [
        { code: 'SAVINGS', displayName: 'Savings' },
        { code: 'CHECKING', displayName: 'Checking' },
      ];

      service.getTypes().subscribe((res) => {
        expect(res).toEqual(types);
        done();
      });

      const req = httpMock.expectOne('/api/account/type');
      expect(req.request.method).toBe('GET');
      req.flush(types);
    });
  });

  describe('#reloadUserAccount and #clearSelectedAccount', () => {
    it('should call reload on userAccountResource (internally tested via spy)', () => {
      const reloadSpy = spyOn<any>(service['userAccountResource'], 'reload');
      service.reloadUserAccount();
      expect(reloadSpy).toHaveBeenCalled();
    });

    it('should call set(null) on userAccountResource', () => {
      const setSpy = spyOn<any>(service['userAccountResource'], 'set');
      service.clearSelectedAccount();
      expect(setSpy).toHaveBeenCalledWith(null);
    });
  });
});
