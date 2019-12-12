import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MandaTestModule } from '../../../test.module';
import { ContactoPessoaUpdateComponent } from 'app/entities/contacto-pessoa/contacto-pessoa-update.component';
import { ContactoPessoaService } from 'app/entities/contacto-pessoa/contacto-pessoa.service';
import { ContactoPessoa } from 'app/shared/model/contacto-pessoa.model';

describe('Component Tests', () => {
  describe('ContactoPessoa Management Update Component', () => {
    let comp: ContactoPessoaUpdateComponent;
    let fixture: ComponentFixture<ContactoPessoaUpdateComponent>;
    let service: ContactoPessoaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MandaTestModule],
        declarations: [ContactoPessoaUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ContactoPessoaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ContactoPessoaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ContactoPessoaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ContactoPessoa(123);
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
        const entity = new ContactoPessoa();
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
