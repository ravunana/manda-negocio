import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { AuditoriaCompraService } from 'app/entities/auditoria-compra/auditoria-compra.service';
import { IAuditoriaCompra, AuditoriaCompra } from 'app/shared/model/auditoria-compra.model';

describe('Service Tests', () => {
  describe('AuditoriaCompra Service', () => {
    let injector: TestBed;
    let service: AuditoriaCompraService;
    let httpMock: HttpTestingController;
    let elemDefault: IAuditoriaCompra;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(AuditoriaCompraService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new AuditoriaCompra(0, 'AAAAAAA', currentDate, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            data: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: elemDefault });
      });

      it('should create a AuditoriaCompra', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            data: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            data: currentDate
          },
          returnedFromService
        );
        service
          .create(new AuditoriaCompra(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a AuditoriaCompra', () => {
        const returnedFromService = Object.assign(
          {
            estado: 'BBBBBB',
            data: currentDate.format(DATE_TIME_FORMAT),
            motivoAlteracaoDocumento: 'BBBBBB',
            origemDocumento: 'BBBBBB',
            hash: 'BBBBBB',
            hashControl: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            data: currentDate
          },
          returnedFromService
        );
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should return a list of AuditoriaCompra', () => {
        const returnedFromService = Object.assign(
          {
            estado: 'BBBBBB',
            data: currentDate.format(DATE_TIME_FORMAT),
            motivoAlteracaoDocumento: 'BBBBBB',
            origemDocumento: 'BBBBBB',
            hash: 'BBBBBB',
            hashControl: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            data: currentDate
          },
          returnedFromService
        );
        service
          .query(expected)
          .pipe(
            take(1),
            map(resp => resp.body)
          )
          .subscribe(body => (expectedResult = body));
        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a AuditoriaCompra', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
