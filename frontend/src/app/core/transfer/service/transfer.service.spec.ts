// transfer.service.spec.ts
import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { MatDialog } from '@angular/material/dialog';
import { TransferService } from './transfer.service';
import { AccountService } from '../../account/service';
import { FormCreateTransferComponent } from '../component/form-create-transfer/form-create-transfer.component';

describe('TransferService', () => {
  let service: TransferService;
  let httpMock: HttpTestingController;
  let dialogSpy: jasmine.SpyObj<MatDialog>;
  let accountServiceSpy: jasmine.SpyObj<AccountService>;

  beforeEach(() => {
    const dialogMock = jasmine.createSpyObj('MatDialog', ['open']);
    const accountMock = jasmine.createSpyObj('AccountService', ['selectedAccountId', 'selectAccount', 'reloadUserAccount']);
    accountMock.selectedAccountId.and.returnValue(null);

    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [
        TransferService,
        { provide: MatDialog, useValue: dialogMock },
        { provide: AccountService, useValue: accountMock }
      ]
    });
    service = TestBed.inject(TransferService);
    httpMock = TestBed.inject(HttpTestingController);
    dialogSpy = TestBed.inject(MatDialog) as jasmine.SpyObj<MatDialog>;
    accountServiceSpy = TestBed.inject(AccountService) as jasmine.SpyObj<AccountService>;
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should validate internal transfer', () => {
    const req = { sourceAccountId: '1', targetAccountId: '2' };
    service.validateInternal(req).subscribe();
    const httpReq = httpMock.expectOne('/api/transfer/validate-internal');
    expect(httpReq.request.method).toBe('POST');
    expect(httpReq.request.body).toEqual(req);
    httpReq.flush({});
  });

  it('should validate external transfer', () => {
    const req = { sourceAccountId: '1', targetAccountNumber: '123' };
    service.validateExternal(req).subscribe();
    const httpReq = httpMock.expectOne('/api/transfer/validate-external');
    expect(httpReq.request.method).toBe('POST');
    expect(httpReq.request.body).toEqual(req);
    httpReq.flush({});
  });

  it('should create a transfer', () => {
    const req = { sourceAccountId: '1', targetAccountNumber: '123', amount: 100, description: 'desc' };
    service.create(req).subscribe();
    const httpReq = httpMock.expectOne('/api/transfer');
    expect(httpReq.request.method).toBe('POST');
    expect(httpReq.request.body).toEqual(req);
    httpReq.flush({});
  });

  it('should open create transfer dialog and handle selection change and reload', () => {
    const dialogRefSpyObj = jasmine.createSpyObj({ afterClosed : { subscribe: (fn: any) => fn('completed') }, close: null });
    dialogSpy.open.and.returnValue(dialogRefSpyObj);
    accountServiceSpy.selectedAccountId.and.returnValue('someAccountId');

    service.openCreateTransferFormDialog('someAccountId');

    expect(accountServiceSpy.selectAccount).not.toHaveBeenCalled();

    accountServiceSpy.selectedAccountId.and.returnValue('otherAccountId');

    service.openCreateTransferFormDialog('someAccountId');

    expect(accountServiceSpy.selectAccount).toHaveBeenCalledWith('someAccountId');
  });

  it('should not open dialog if accountId is undefined', () => {
    service.openCreateTransferFormDialog(undefined);
    expect(dialogSpy.open).not.toHaveBeenCalled();
  });
});
