import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { UserPoliciesService } from './user-policies.service';
import { Policy } from '../model';
import { AuthService } from '../../auth/service';
import { SorterService } from '../../shared/service/sorter.service';
import { of } from 'rxjs';
import { provideHttpClient } from '@angular/common/http';

describe('UserPoliciesService', () => {
  let service: UserPoliciesService;
  let httpMock: HttpTestingController;
  let authServiceSpy: jasmine.SpyObj<AuthService>;
  let sorterServiceSpy: jasmine.SpyObj<SorterService>;

  beforeEach(() => {
    const authSpy = jasmine.createSpyObj('AuthService', ['isLoggedIn']);
    const sorterSpy = jasmine.createSpyObj('SorterService', ['sortListByField']);

    TestBed.configureTestingModule({
      providers: [
        UserPoliciesService,
        provideHttpClient(),
        provideHttpClientTesting(),
        { provide: AuthService, useValue: authSpy },
        { provide: SorterService, useValue: sorterSpy },
      ],
    });

    service = TestBed.inject(UserPoliciesService);
    httpMock = TestBed.inject(HttpTestingController);
    authServiceSpy = TestBed.inject(AuthService) as jasmine.SpyObj<AuthService>;
    sorterServiceSpy = TestBed.inject(SorterService) as jasmine.SpyObj<SorterService>;
  });

  afterEach(() => {
    httpMock.verify();
  });

  describe('#getUserPoliciesList', () => {
    it('should return empty array if user is not logged in', (done: DoneFn) => {
      authServiceSpy.isLoggedIn.and.returnValue(false);

      service.getUserPoliciesList().subscribe(policies => {
        expect(policies).toEqual([]);
        done();
      });
    });
  });

  describe('#reloadUserPolicies and #clearUserPolicies', () => {
    it('should call reload on userPoliciesResource', () => {
      const reloadSpy = spyOn<any>(service['userPoliciesResource'], 'reload');
      service.reloadUserPolicies();
      expect(reloadSpy).toHaveBeenCalled();
    });

    it('should set empty array on userPoliciesResource when clearing', () => {
      const setSpy = spyOn<any>(service['userPoliciesResource'], 'set');
      service.clearUserPolicies();
      expect(setSpy).toHaveBeenCalledWith([]);
    });
  });

  describe('#sortByField', () => {
    it('should toggle sort direction if sorting by same field', () => {
      sorterServiceSpy.sortListByField.and.callFake((list) => list);

      // initially sortField = 'policyNumber' and direction = 'asc'
      service.sortByField('policyNumber');
      expect(service.sortDirectionValue()).toBe('desc');
      expect(sorterServiceSpy.sortListByField).toHaveBeenCalled();

      // toggling again back to 'asc'
      service.sortByField('policyNumber');
      expect(service.sortDirectionValue()).toBe('asc');
    });

    it('should set new sort field and direction asc if different field', () => {
      sorterServiceSpy.sortListByField.and.callFake((list) => list);

      service.sortByField('startDate');
      expect(service.sortFieldValue()).toBe('startDate');
      expect(service.sortDirectionValue()).toBe('asc');
      expect(sorterServiceSpy.sortListByField).toHaveBeenCalled();
    });
  });
});
