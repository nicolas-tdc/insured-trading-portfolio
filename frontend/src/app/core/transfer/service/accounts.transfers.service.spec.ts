// account-transfers.service.spec.ts
import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { AccountTransfersService } from './account-transfers.service';

describe('AccountTransfersService', () => {
  let service: AccountTransfersService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [AccountTransfersService]
    });

    service = TestBed.inject(AccountTransfersService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should select and get selectedAccountId', () => {
    service.selectAccount('abc');
    expect(service.selectedAccountId()).toBe('abc');
  });

  it('should get account transfers with valid accountId', () => {
    const accountId = '123';
    service.getAccountTransfers(accountId).subscribe();
    const req = httpMock.expectOne(`/api/transfer/account/${accountId}`);
    expect(req.request.method).toBe('GET');
    req.flush([]);
  });

  it('should get account transfers with null accountId and return empty array', (done) => {
    service.getAccountTransfers(null).subscribe(data => {
      expect(data).toEqual([]);
      done();
    });
  });

  it('should reload account transfers', () => {
    spyOn(service['accountTransfersResource'], 'reload');
    service.reloadAccountTransfers();
    expect(service['accountTransfersResource'].reload).toHaveBeenCalled();
  });

  it('should clear account transfers', () => {
    service.clearAccountTransfers();
    expect(service['accountTransfersResource'].value()).toEqual([]);
  });
});
