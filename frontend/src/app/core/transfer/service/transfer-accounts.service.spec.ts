// transfer-accounts.service.spec.ts
import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { TransferAccountsService } from './transfer-accounts.service';

describe('TransferAccountsService', () => {
  let service: TransferAccountsService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [TransferAccountsService]
    });

    service = TestBed.inject(TransferAccountsService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should select and clear source account', () => {
    service.selectSourceAccount('123');
    expect(service['sourceAccountId']()).toBe('123');
    service.clearSourceAccount();
    expect(service['sourceAccountId']()).toBeNull();
  });

  it('should select and clear internal account', () => {
    service.selectInternalAccount('123');
    expect(service['internalTargetAccountId']()).toBe('123');
    service.clearInternalAccount();
    expect(service['internalTargetAccountId']()).toBeNull();
  });

  it('should select and clear external account', () => {
    service.selectExternalAccount('acc123');
    expect(service['externalTargetAccountNumber']()).toBe('acc123');
    service.clearExternalAccount();
    expect(service['externalTargetAccountNumber']()).toBeNull();
  });

  it('should get internal target account', () => {
    const sourceId = 's1';
    const targetId = 't1';
    service.getInternalTargetAccount(sourceId, targetId).subscribe();
    const req = httpMock.expectOne('/api/transfer/validate-internal');
    expect(req.request.method).toBe('POST');
    expect(req.request.body).toEqual({ sourceAccountId: sourceId, targetAccountId: targetId });
    req.flush({});
  });

  it('should get external target account', () => {
    const sourceId = 's1';
    const targetNumber = 'num123';
    service.getExternalTargetAccount(sourceId, targetNumber).subscribe();
    const req = httpMock.expectOne('/api/transfer/validate-external');
    expect(req.request.method).toBe('POST');
    expect(req.request.body).toEqual({ sourceAccountId: sourceId, targetAccountNumber: targetNumber });
    req.flush({});
  });

  it('should clear target account and reload', () => {
    service.clearTargetAccount();
    expect(service['targetAccountResource'].value()).toBeNull();
    spyOn(service['targetAccountResource'], 'reload');
    service.reloadTargetAccount();
    expect(service['targetAccountResource'].reload).toHaveBeenCalled();
  });

  it('should clear all transfer accounts', () => {
    spyOn(service, 'clearSourceAccount');
    spyOn(service, 'clearInternalAccount');
    spyOn(service, 'clearExternalAccount');
    spyOn(service, 'clearTargetAccount');
    service.clearTransferAccounts();
    expect(service.clearSourceAccount).toHaveBeenCalled();
    expect(service.clearInternalAccount).toHaveBeenCalled();
    expect(service.clearExternalAccount).toHaveBeenCalled();
    expect(service.clearTargetAccount).toHaveBeenCalled();
  });
});
