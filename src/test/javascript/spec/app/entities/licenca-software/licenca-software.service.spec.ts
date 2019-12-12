import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { LicencaSoftwareService } from 'app/entities/licenca-software/licenca-software.service';
import { ILicencaSoftware, LicencaSoftware } from 'app/shared/model/licenca-software.model';

describe('Service Tests', () => {
  describe('LicencaSoftware Service', () => {
    let injector: TestBed;
    let service: LicencaSoftwareService;
    let httpMock: HttpTestingController;
    let elemDefault: ILicencaSoftware;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(LicencaSoftwareService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new LicencaSoftware(0, 'AAAAAAA', currentDate, currentDate, currentDate, 0, 'AAAAAAA', 0, 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            inicio: currentDate.format(DATE_TIME_FORMAT),
            fim: currentDate.format(DATE_TIME_FORMAT),
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

      it('should create a LicencaSoftware', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            inicio: currentDate.format(DATE_TIME_FORMAT),
            fim: currentDate.format(DATE_TIME_FORMAT),
            data: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            inicio: currentDate,
            fim: currentDate,
            data: currentDate
          },
          returnedFromService
        );
        service
          .create(new LicencaSoftware(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a LicencaSoftware', () => {
        const returnedFromService = Object.assign(
          {
            tipoSubscricao: 'BBBBBB',
            inicio: currentDate.format(DATE_TIME_FORMAT),
            fim: currentDate.format(DATE_TIME_FORMAT),
            data: currentDate.format(DATE_TIME_FORMAT),
            valor: 1,
            codigo: 'BBBBBB',
            numeroUsuario: 1,
            numeroEmpresa: 1
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            inicio: currentDate,
            fim: currentDate,
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

      it('should return a list of LicencaSoftware', () => {
        const returnedFromService = Object.assign(
          {
            tipoSubscricao: 'BBBBBB',
            inicio: currentDate.format(DATE_TIME_FORMAT),
            fim: currentDate.format(DATE_TIME_FORMAT),
            data: currentDate.format(DATE_TIME_FORMAT),
            valor: 1,
            codigo: 'BBBBBB',
            numeroUsuario: 1,
            numeroEmpresa: 1
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            inicio: currentDate,
            fim: currentDate,
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

      it('should delete a LicencaSoftware', () => {
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
