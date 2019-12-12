import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MandaTestModule } from '../../../test.module';
import { CoordenadaBancariaUpdateComponent } from 'app/entities/coordenada-bancaria/coordenada-bancaria-update.component';
import { CoordenadaBancariaService } from 'app/entities/coordenada-bancaria/coordenada-bancaria.service';
import { CoordenadaBancaria } from 'app/shared/model/coordenada-bancaria.model';

describe('Component Tests', () => {
  describe('CoordenadaBancaria Management Update Component', () => {
    let comp: CoordenadaBancariaUpdateComponent;
    let fixture: ComponentFixture<CoordenadaBancariaUpdateComponent>;
    let service: CoordenadaBancariaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MandaTestModule],
        declarations: [CoordenadaBancariaUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(CoordenadaBancariaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CoordenadaBancariaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CoordenadaBancariaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CoordenadaBancaria(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new CoordenadaBancaria();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
