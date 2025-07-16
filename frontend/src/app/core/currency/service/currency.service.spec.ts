import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { CurrencyService } from './currency.service';
import { Currency } from '../model';
import { provideHttpClient } from '@angular/common/http';

describe('CurrencyService', () => {
  let service: CurrencyService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        CurrencyService,
        provideHttpClient(),
        provideHttpClientTesting(),
      ],
    });

    service = TestBed.inject(CurrencyService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  describe('#getList', () => {
    it('should GET list of currencies', (done: DoneFn) => {
      const mockCurrencies: Currency[] = [
        { currencyCode: 'USD', currencySymbol: '$', currencyFractionDigits: 2 },
        { currencyCode: 'EUR', currencySymbol: '€', currencyFractionDigits: 2 },
      ];

      service.getList().subscribe(currencies => {
        expect(currencies).toEqual(mockCurrencies);
        done();
      });

      const req = httpMock.expectOne('/api/currency');
      expect(req.request.method).toBe('GET');
      req.flush(mockCurrencies);
    });
  });
});
